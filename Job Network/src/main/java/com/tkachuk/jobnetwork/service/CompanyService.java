package com.tkachuk.jobnetwork.service;

import com.tkachuk.common.dto.CompanyDto;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public void saveCompanyData(CompanyDto companyRequest) {
        Company company = new Company(companyRequest.getName());
        companyRepository.save(company);
    }

    public void deleteCompanyData(Long id) {
        companyRepository.deleteById(id);
    }

    public Optional<Company> findCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company updateCompanyById(Long id, CompanyDto companyRequest) {
        Optional<Company> company = findCompanyById(id);
        if (company.isPresent()) {
            Company company1 = company.get();
            company1.setName(companyRequest.getName());
            return companyRepository.save(company1);
        }
        else {
            return null;
        }
    }
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
