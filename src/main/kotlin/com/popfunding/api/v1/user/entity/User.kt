package com.popfunding.api.v1.user.entity

import com.popfunding.api.v1.user.dto.UserDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    private var username: String,

    private var password: String,

    @ElementCollection(fetch = FetchType.LAZY)
    var roles: MutableSet<String>

) : UserDetails {

    constructor(userDto: UserDto, roles: MutableSet<String>) : this(null, userDto.username, userDto.password, roles)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority("USER"))
        //return roles.map { role -> SimpleGrantedAuthority(role) }.toMutableSet()
    }

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String? = username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String? = password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}