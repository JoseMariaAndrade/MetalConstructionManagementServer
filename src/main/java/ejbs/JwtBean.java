package ejbs;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;

@Stateless(name = "JwtEJB")
public class JwtBean {

    private static final PrivateKey PRIVATE_KEY;
    private static final int TOKEN_VALIDITY = 14400;
    private static final String CLAIM_ROLES = "groups";
    private static final String ISSUER = "quickstart-jwt-issuer";
    private static final String AUDIENCE = "jwt-audience";

    static {
        FileInputStream fileInputStream = null;
        char[] password = "secret".toCharArray();
        String alias = "alias";
        PrivateKey privateKey = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            String configDir = System.getProperty("jboss.server.config.dir");
            String keystorePath = configDir + File.separator + "jwt.keystore";
            fileInputStream = new FileInputStream(keystorePath);
            keyStore.load(fileInputStream, password);
            Key key = keyStore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                privateKey = (PrivateKey) key;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        PRIVATE_KEY = privateKey;
    }

    public String createJwt(final String subject, final String[] roles)
            throws Exception {
        JWSSigner signer = new RSASSASigner(PRIVATE_KEY);
        JsonArrayBuilder rolesBuilder = Json.createArrayBuilder();
        for (String role : roles) {
            rolesBuilder.add(role);
        }

        JsonObjectBuilder claimsBuilder = Json.createObjectBuilder()
                .add("sub", subject)
                .add("iss", ISSUER)
                .add("aud", AUDIENCE)
                .add(CLAIM_ROLES, rolesBuilder.build())
                .add("exp", ((System.currentTimeMillis() / 1000) + TOKEN_VALIDITY));

        JWSObject jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(new JOSEObjectType("jwt")).build(), new Payload(claimsBuilder.build().toString()));

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }


}