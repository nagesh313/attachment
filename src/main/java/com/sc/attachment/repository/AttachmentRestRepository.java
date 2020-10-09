package com.sc.attachment.repository;

import com.sc.attachment.entity.Attachment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "attachments", path = "attachments")
public interface AttachmentRestRepository extends PagingAndSortingRepository<Attachment, String> {
    List<Attachment> findByName(@Param("name") String name);
}
