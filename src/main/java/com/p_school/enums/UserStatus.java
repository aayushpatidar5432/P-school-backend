package com.p_school.enums;

public enum UserStatus {

	ACTIVE, // User Login कर सकता है

	INACTIVE, // User Active नहीं है (Temporarily Disabled)

	PENDING, // Approval का इंतजार (Institute Request या New User)

	SUSPENDED, // Super Admin/Admin ने Suspend किया

	BLOCKED, // Security Reason से Block

	DELETED // Soft Delete (Database में रहेगा)
}

