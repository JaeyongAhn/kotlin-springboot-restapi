package com.popfunding.api.config.security

import com.popfunding.api.v1.user.service.UserDetailsServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest
import com.popfunding.api.config.logger
import com.popfunding.api.v1.admin.service.AdminService

@Component
class JwtTokenProvider {
    var logger = logger()

    private var secretkey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    private val tokenValidMilisecond = 1000L * 60 * 60 * 24

    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl

    @Autowired
    lateinit var adminService: AdminService

    fun createToken(uid: String, role: String): String {
        val claims: Claims = Jwts.claims().setSubject(uid)
        claims["role"] = role
        val now: Date = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidMilisecond))
            .signWith(secretkey)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = if (getRole(token) == "ADMIN") {
            adminService.loadUserByUsername(getUsername(token))
        } else {
            userDetailsService.loadUserByUsername(getUsername(token))
        }
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        logger.info(Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).body.subject)
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).body.subject
    }

    fun getRole(token: String): String {
        logger.info(Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).body.get("role").toString())
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).body.get("role").toString()
    }

    fun resolveToken(req: HttpServletRequest?): String? {
        return req?.getHeader("X-AUTH-TOKEN")
    }

    fun validateToken(jwtToken: String?): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(jwtToken)
            return !claims.body.expiration.before(Date())
        } catch (e: Exception) {
        }
        return false
    }
}