package com.mszgajewski.NotesOnline.repository;

import com.mszgajewski.NotesOnline.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {

    @Query("from Notes as n where n.User.id=:uid")
    Page<Notes> findNotesByUser(@Param("uid") long uid, Pageable pageable);
}
