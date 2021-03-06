package com.example.webservice.chat.services.beans

import com.example.webservice.chat.models.entities.ChatRoom
import com.example.webservice.chat.repositories.ChatRoomRepository
import com.example.webservice.chat.services.ChatRoomService
import com.example.webservice.commons.PageAttr
import com.example.webservice.commons.utils.ExceptionUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatRoomServiceBean @Autowired constructor(
        private val chatRoomRepository: ChatRoomRepository
) : ChatRoomService {

    override fun search(query: String, page: Int): Page<ChatRoom> {
        return this.chatRoomRepository.search(query, PageAttr.getPageRequest(page))
    }

    override fun save(entity: ChatRoom): ChatRoom {
        return this.chatRoomRepository.save(entity)
    }

    override fun find(id: Long): Optional<ChatRoom> {
        return this.chatRoomRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val chatRoom = this.find(id).orElseThrow { ExceptionUtil.getNotFound("ChatRoom", id) }
            chatRoom.isDeleted = true
            this.save(chatRoom)
            return
        }
        return this.chatRoomRepository.deleteById(id)
    }

}