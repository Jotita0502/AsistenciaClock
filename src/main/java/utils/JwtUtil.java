package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import java.util.Date;


public class JwtUtil {

    private static final String SECRET = "MI_CLAVE_SUPER_SECRETA";

    public static String generarToken(int id, String correo) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        String token = JWT.create()
                .withClaim("id", id)
                .withClaim("correo", correo)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);

        return token;
    }
        public static boolean validarToken(String token) {

        try {

            Algorithm algorithm =
                    Algorithm.HMAC256(SECRET);

            JWT.require(algorithm)
                    .build()
                    .verify(token);

            return true;

        } catch (Exception e) {

            return false;
        }
        
    }
        
      public static DecodedJWT obtenerTokenDecodificado(String token) {

    try {

        Algorithm algorithm =
                Algorithm.HMAC256(SECRET);

        JWTVerifier verifier =
                JWT.require(algorithm).build();

        return verifier.verify(token);

    } catch (Exception e) {

        return null;
    }
    }
     
}