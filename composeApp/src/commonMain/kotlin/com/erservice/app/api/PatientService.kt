package com.erservice.app.api

import com.erservice.app.api.dto.AdmitPatientRequest
import com.erservice.app.api.dto.PaginatedResponse
import com.erservice.app.api.dto.UpdatePatientStatusRequest
import io.ktor.client.* 
import io.ktor.client.call.* 
import io.ktor.client.request.* 
import io.ktor.http.* 

interface PatientService {
    suspend fun getPatients(): List<Patient>
    suspend fun getNewPatients(): List<Patient>
    suspend fun admitPatient(request: AdmitPatientRequest)
    suspend fun handlePatient(id: Long)
    suspend fun updatePatientStatus(id: Long, request: UpdatePatientStatusRequest)
}

class PatientServiceImpl(private val httpClient: HttpClient) : PatientService {

    override suspend fun getPatients(): List<Patient> {
        val response: PaginatedResponse<Patient> = httpClient.get("/patients").body()
        return response.data
    }

    override suspend fun getNewPatients(): List<Patient> {
        val response: PaginatedResponse<Patient> = httpClient.get("/patients/new").body()
        return response.data
    }

    override suspend fun admitPatient(request: AdmitPatientRequest) {
        httpClient.post("/patients") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun handlePatient(id: Long) {
        httpClient.put("/patients/$id/handle").body<Unit>()
    }

    override suspend fun updatePatientStatus(id: Long, request: UpdatePatientStatusRequest) {
        httpClient.put("/patients/$id/status") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}