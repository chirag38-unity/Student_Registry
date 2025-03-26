package com.cr.studentregistry.data.repository

import com.cr.studentregistry.data.models.StudentDTO
import com.cr.studentregistry.data.models.toStudent
import com.cr.studentregistry.data.models.toStudentDTO
import com.cr.studentregistry.domain.models.Student
import com.cr.studentregistry.utils.DataError
import com.cr.studentregistry.utils.Result
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.snapshots
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StudentsRepository constructor(
    private val studentsDb : CollectionReference
) {

    suspend fun getStudents() : Flow<List<Student>> = studentsDb
        .snapshots()
        .map { snapshot ->
            snapshot.documents.map {
                Napier.d("Received document: ${it.data}")
                val item = it.toObject(StudentDTO::class.java)
                Napier.d("Received student: $item")
                item?.id = it.id
                item!!.toStudent()
            }
        }
        .catch { exception ->
            Napier.e("Error receiving students", exception)
            emit(emptyList())
        }

    suspend fun getStudent(id: String) : Result<Student,DataError> {
        val student = studentsDb.document(id).get().await()
        return if (student.exists()) {
            Result.Success(student.toObject(StudentDTO::class.java)!!.toStudent())
        } else {
            Result.Error(DataError.NetworkError.SERVER_ERROR)
        }
    }

    suspend fun addStudent(student: Student) {
        studentsDb.add(student.toStudentDTO())
    }

    suspend fun updateStudent(student: Student) {
        studentsDb.document(student.id!!).set(student.toStudentDTO())
    }

    suspend fun deleteStudent(id: String) {
        studentsDb.document(id).delete()
    }

}