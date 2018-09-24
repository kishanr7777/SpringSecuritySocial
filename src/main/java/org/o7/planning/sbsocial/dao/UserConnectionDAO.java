package org.o7.planning.sbsocial.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.o7.planning.sbsocial.entity.UserConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserConnectionDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	public UserConnection findUserConnectionByUserProvidedId(String userProviderId){
		try {
			String sql = "select e from "+UserConnection.class.getName()+"e"+"where e.userProviderId = :userProviderId";
			 Query query = entityManager.createQuery(sql, UserConnection.class);
	            query.setParameter("userProviderId", userProviderId);
	 
	            List<UserConnection> list = query.getResultList();
	 
	            return list.isEmpty() ? null : list.get(0);
		}  catch (NoResultException e) {
            return null;
        }
	}

}
