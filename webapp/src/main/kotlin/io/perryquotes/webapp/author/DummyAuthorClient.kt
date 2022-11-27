//package io.perryquotes.webapp.author
//
//import org.springframework.stereotype.Component
//import java.time.LocalDateTime
//import java.util.*
//
//@Component
//class DummyAuthorClient : AuthorClient {
//
//    data class Author(
//        val uuid: UUID,
//        val name: String,
//        val createdDate: LocalDateTime = LocalDateTime.now(),
//        val lastModifiedDate: LocalDateTime = LocalDateTime.now()
//    )
//
//    private final val perry = Author(UUID.randomUUID(), "Perry Rhodan")
//    private final val atlan = Author(UUID.randomUUID(), "Atlan")
//    private final val thora = Author(UUID.randomUUID(), "Thora")
//    private final val gucky = Author(UUID.randomUUID(), "Gucky")
//    private final val icho = Author(UUID.randomUUID(), "Icho Tolot")
//
//    val authors = mutableMapOf(
//        perry.uuid to perry,
//        atlan.uuid to atlan,
//        thora.uuid to thora,
//        gucky.uuid to gucky,
//        icho.uuid to icho)
//
//    override fun getAllAuthors(): List<Author> {
//        return authors.values.sortedBy { it.name }.toList()
//    }
//
//    override fun getAuthorByUuid(uuid: UUID): Author? {
//        return authors[uuid]
//    }
//
//    override fun createAuthor(authorForm: AuthorForm): Author {
//        val newAuthor= Author(UUID.randomUUID(), authorForm.name!!)
//        authors[newAuthor.uuid] = newAuthor
//        return newAuthor
//    }
//
//    override fun updateAuthor(uuid: UUID, authorForm: AuthorForm): Author {
//        val existing = authors[uuid] ?: throw IllegalArgumentException("Author[uuid=$uuid] found")
//        authors[uuid] = existing.copy(name = authorForm.name!!, lastModifiedDate = LocalDateTime.now())
//        return authors[uuid]!!
//    }
//}
