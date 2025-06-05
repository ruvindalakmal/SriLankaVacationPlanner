package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
