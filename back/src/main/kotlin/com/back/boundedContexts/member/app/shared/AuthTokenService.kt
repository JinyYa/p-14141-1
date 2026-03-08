package com.back.boundedContexts.member.app.shared

import com.back.boundedContexts.member.domain.shared.Member
import com.back.boundedContexts.member.dto.shared.AccessTokenPayload
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.Base64

@Service
class AuthTokenService {
    fun genAccessToken(member: Member): String =
        Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString("${member.id}:${member.username}:${member.nickname}".toByteArray(StandardCharsets.UTF_8))

    fun payload(accessToken: String): AccessTokenPayload? {
        val decoded = runCatching {
            String(Base64.getUrlDecoder().decode(accessToken), StandardCharsets.UTF_8)
        }.getOrNull() ?: return null

        val parts = decoded.split(":", limit = 3)
        if (parts.size != 3) return null

        return AccessTokenPayload(
            id = parts[0].toIntOrNull() ?: return null,
            username = parts[1],
            name = parts[2],
        )
    }
}
