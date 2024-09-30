package edu.gustdev.springboot.cruddemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import edu.gustdev.springboot.cruddemo.entities.Employee;

@Repository
@RepositoryRestResource(path = "members") 
/* 
 * O Spring Data REST usa as entidades  definidas no repositório para criar os end-points: 
 * se a entidade é Employee, ele a colocará, como padrão da seguinte forma: "/employees". A partir dessa
 * URL, o Spring Data REST irá retornar a lista de todos os employees no banco de dados
 * quando uma requisição do tipo GET for feita pelo client. Porém, usando essa anotação,
 * @RepositoryRestResource(path = "members") irá sobreescrever a URL padrão gerada pelo Spring Data REST
 * e esta URL agora será /members.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
