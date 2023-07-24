package com.rova.transactionservice.util;

import static com.rova.transactionservice.util.Constants.IAPPENDABLE_REF_SEPARATOR;

public class IAppendableReferenceUtils {
    private static String[] getParts(String reference) {
        if (reference == null || !reference.contains(IAPPENDABLE_REF_SEPARATOR)) {
            return null;
        }
        String[] parts = reference.split(IAPPENDABLE_REF_SEPARATOR);
        if (parts.length != 2) return null;

        return parts;
    }

    public static Long getIdFrom(String reference) {
        try {
            return Long.parseLong(getParts(reference)[0]);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    public static String getReferenceFrom(String reference) {
        return getParts(reference)[1];
    }
}
