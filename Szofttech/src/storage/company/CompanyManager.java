package storage.company;

import back.FileManager;
import common.Company;
import storage.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyManager implements Manager<Company> {

    private static final String fileName = "companies.dat";
    private List<Company> companies;
    private FileManager fileManager;

    public CompanyManager(FileManager fileManager) {
        this.fileManager = fileManager;
        readData();
    }

    @Override
    public void readData() {

        try {
            List<String> datas = fileManager.read(fileName);
            companies = new ArrayList<>();
            for (String data: datas) {
                String[] dataColumn = data.split(",");
                Company company = new Company(
                        dataColumn[0],
                        dataColumn[1],
                        dataColumn[2]
                    );
                companies.add(company);
            }
        } catch (IOException e) {
            // TODO Logging
        }

    }

    @Override
    public void delete(Company content) {
        companies.removeIf(company -> company.getId().equals(content.getId()));
        try {
            fileManager.remove(fileName, content.toString());
        } catch (IOException e) {
            // TODO Logging
        }
    }

    @Override
    public Company get(String id) {
        return companies
                .stream()
                .filter(company -> company.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public void add(Company company) {
        companies.add(company);
        try {
            fileManager.add(fileName, company.toString());
        } catch (IOException e) {
            // TODO Logging
        }
    }

    @Override
    public List<Company> list() {
        return companies;
    }
}
