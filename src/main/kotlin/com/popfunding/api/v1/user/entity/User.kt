package com.popfunding.api.v1.admin.entity

import com.popfunding.api.v1.user.dto.UserDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
class User : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    private var username: String? = null

    private var password: String? = null

    constructor(userDto: UserDto, password: String) {
        username = userDto.username
        this.password = password
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority("ROLE_USER"))
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