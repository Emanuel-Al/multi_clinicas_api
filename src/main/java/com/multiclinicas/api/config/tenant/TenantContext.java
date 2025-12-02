package com.multiclinicas.api.config.tenant;

public class TenantContext {
    private static final ThreadLocal<Long> currentTenant = new ThreadLocal<>();

    public static void setClinicId(Long clinicId) {
        currentTenant.set(clinicId);
    }

    public static Long getClinicId() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
