package com.multiclinicas.api.config.tenant;

import com.multiclinicas.api.exceptions.ResourceNotFoundException;
import com.multiclinicas.api.repositories.ClinicaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantInterceptorTest {

    @Mock
    private ClinicaRepository clinicaRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private TenantInterceptor tenantInterceptor;

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void preHandle_ShouldReturnTrue_WhenHeaderIsValidAndClinicExists() {
        when(request.getHeader("X-Clinic-ID")).thenReturn("1");
        when(clinicaRepository.existsById(1L)).thenReturn(true);

        boolean result = tenantInterceptor.preHandle(request, response, new Object());

        assertTrue(result);
        assertEquals(1L, TenantContext.getClinicId());
    }

    @Test
    void preHandle_ShouldThrowException_WhenHeaderIsMissing() {
        when(request.getHeader("X-Clinic-ID")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> tenantInterceptor.preHandle(request, response, new Object()));
    }

    @Test
    void preHandle_ShouldThrowException_WhenHeaderIsBlank() {
        when(request.getHeader("X-Clinic-ID")).thenReturn("");

        assertThrows(IllegalArgumentException.class,
                () -> tenantInterceptor.preHandle(request, response, new Object()));
    }

    @Test
    void preHandle_ShouldThrowException_WhenHeaderIsNotANumber() {
        when(request.getHeader("X-Clinic-ID")).thenReturn("abc");

        assertThrows(IllegalArgumentException.class,
                () -> tenantInterceptor.preHandle(request, response, new Object()));
    }

    @Test
    void preHandle_ShouldThrowException_WhenClinicDoesNotExist() {
        when(request.getHeader("X-Clinic-ID")).thenReturn("999");
        when(clinicaRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> tenantInterceptor.preHandle(request, response, new Object()));
    }
}
