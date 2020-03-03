package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

/**
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */

@Component
public class JWTToken {

	private static final String TOKEN_SECRET_CODE = "Niranjan";
	public String createJwtToken(long id) {
		String generatedToken = null;
		try {
			generatedToken = JWT.create().withClaim("id", id).sign(Algorithm.HMAC256(TOKEN_SECRET_CODE));
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return generatedToken;
	}

	public Long decodeToken(String jwtToken) {
		Long userId = (long) 0;
		try {
			if (jwtToken != null) {
				Verification verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET_CODE));
				JWTVerifier jwtverifier = verification.build();
				DecodedJWT decodedjwt = jwtverifier.verify(jwtToken);
				Claim claim = decodedjwt.getClaim("id");
				userId = claim.asLong();
			}
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return userId;
	}
}
