package com.example.websecurity.annotation

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasRole('ROLE_ADMIN')")
annotation class Admin
@PreAuthorize("hasRole('ROLE_MEMBER')")
annotation class Member
@PreAuthorize("hasRole('ROLE_DEVELOPER')")
annotation class Developer
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
annotation class AdminMember
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEVELOPER')")
annotation class AdminDeveloper
@PreAuthorize("hasAnyRole('ROLE_DEVELOPER', 'ROLE_MEMBER')")
annotation class DeveloperMember