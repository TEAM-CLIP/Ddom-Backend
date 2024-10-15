package com.clip.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.security.PublicKey
import java.util.*
import javax.crypto.SecretKey


object JwtProvider {

    inline fun <reified T: JwtClaims> createToken(
        jwtPayload: JwtPayload<T>,
        secretKey: String
    ): String{
        return Jwts.builder()
            .issuer(jwtPayload.issuer)
            .subject(jwtPayload.subject)
            .claims(jwtPayload.claims.getClaims())
            .issuedAt(jwtPayload.issuedAt)
            .signWith(generateKey(secretKey))
            .expiration(Date(jwtPayload.issuedAt.time + jwtPayload.expireTime))
            .compact()

    }

    fun generateKey(
        secretKey: String
    ): SecretKey{
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    inline fun <reified T: JwtClaims> resolveToken(
        token: String,
        secret: String,
    ): JwtPayload<T>{
        val claims = Jwts.parser()
            .verifyWith(generateKey(secret))
            .build()
            .parseSignedClaims(token)
            .payload

        return JwtPayload(
            issuedAt = claims.issuedAt,
            issuer = claims.issuer,
            subject = claims.subject,
            expireTime = claims.expiration.time - claims.issuedAt.time,
            claims = JwtClaims.convertFromClaims(claims)
        )
    }

    fun extractClaimsByKey(
        token: String,
        publicKey: PublicKey,
        issuer: String,
        vararg claimKeys: String
    ): Map<String, String> {
        val jws = getTokenJws(token, publicKey, issuer)
        return extractClaims(jws, *claimKeys)
    }

    private fun getTokenJws(
        token: String,
        publicKey: PublicKey,
        issuer: String
    ): Jws<Claims> {
        return Jwts.parser()
            .verifyWith(publicKey)
            .requireIssuer(issuer)
            .build()
            .parseSignedClaims(token)
    }

    private fun extractClaims(jws: Jws<Claims>, vararg claimKeys: String): Map<String, String> {
        val claims = jws.payload
        return claimKeys.associateWith { key ->
            claims[key]?.toString() ?: throw IllegalArgumentException("Claim $key not found in JWT")
        }
    }


}