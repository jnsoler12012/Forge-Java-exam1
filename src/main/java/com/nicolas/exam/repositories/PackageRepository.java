package com.nicolas.exam.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nicolas.exam.models.Package;

@Repository
public interface PackageRepository extends BaseRepository<Package> {
	List<Package> findAllByName(String name);
}
