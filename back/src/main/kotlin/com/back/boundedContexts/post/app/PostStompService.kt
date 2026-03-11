package com.back.boundedContexts.post.app

import com.back.boundedContexts.post.domain.Post
import com.back.boundedContexts.post.dto.PostWithContentDto
import com.back.global.websocket.app.StompService
import org.springframework.stereotype.Service

@Service
class PostStompService(
    private val stompService: StompService,
) {
    fun notifyPostModified(post: Post) {
        stompService.send("/topic/posts/${post.id}/modified", PostWithContentDto(post))
    }

    fun notifyNewPost(post: Post) {
        stompService.send(
            "/topic/posts/new",
            mapOf(
                "id" to post.id,
                "title" to post.title,
                "authorId" to post.author.id,
                "authorName" to post.author.nickname,
                "authorProfileImgUrl" to post.author.profileImgUrlOrDefault,
                "createdAt" to post.createdAt.toString(),
            )
        )
    }
}
