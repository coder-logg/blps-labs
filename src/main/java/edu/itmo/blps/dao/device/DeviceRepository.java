package edu.itmo.blps.dao.device;

import edu.itmo.blps.dao.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Integer> {
	Optional<Device> findDeviceByNameAndCompany(String name, Company company);
	List<Device> findAllByCompany(Company company);
	List<Device> findAllByCompany_Id(Integer companyId);
}
