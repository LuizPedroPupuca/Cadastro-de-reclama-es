package br.com.zup.edu.cadastrodereclamacoes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CelularUtils {

    public static String anonymize(String celular) {
        String regex = "([0-9]{3})\\.([0-9]{3})\\.([0-9]{3})\\-([0-9]{2})";
        String masked = celular.replaceAll(regex, "+$1.***.***-$4");
        return masked;
    }

    public static String hash(String celular) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            byte[] hash = digest.digest(celular.getBytes(StandardCharsets.UTF_8));
            return toHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalStateException("Erro ao gerar hash de um celular: " + celular);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }
}
