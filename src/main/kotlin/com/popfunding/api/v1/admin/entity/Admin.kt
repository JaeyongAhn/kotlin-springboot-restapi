package com.popfunding.api.v1.admin.entity

import com.popfunding.api.v1.admin.dto.AdminDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
class Admin : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    private var username: String? = null

    private var password: String? = null

    constructor(adminDto: AdminDto, password: String) {
        username = adminDto.username
        this.password = password
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority("ROLE_ADMIN"))
    }

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String? = username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String? = password

    fun setPassword(password: String) {
        this.password = password
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}