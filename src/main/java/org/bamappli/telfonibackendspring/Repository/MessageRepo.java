package org.bamappli.telfonibackendspring.Repository;

import org.bamappli.telfonibackendspring.Entity.Message;
import org.bamappli.telfonibackendspring.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT CASE " +
            "WHEN m.utilisateur = m.discussion.acheteur THEN m.discussion.vendeur " +
            "ELSE m.discussion.acheteur " +
            "END " +
            "FROM Message m " +
            "WHERE m.id = :messageId")
    Utilisateur findReceiver(@Param("messageId") Long messageId);
}