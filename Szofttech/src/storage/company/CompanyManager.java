package storage.company;

import src.back.FileManager;
import src.common.Company;
import src.storage.Manager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompanyManager implements Manager {

    private static final String filePath = "";
    private List<Company> companies;
    private FileManager fileManager;

    public CompanyManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void readData() {

    }

    @Override
    public void delete(String id) {
        companies = companies
                .stream()
                .filter(company -> company.getId().equals(id))
                .collect(Collectors.toList());
        fileManager.deleteLine(filePath, id);
    }

    @Override
    public Optional<Company> get(String id) {
        return companies
                .stream()
                .filter(company -> company.getId().equals(id))
                .findFirst();
    }

    @Override
    public void add(Object company) {
        fileManager.addLine(filePath, company);
    }

    @Override
    public List<Company> list() {
        return companies;
    }
}
