package mykalah.service;


import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Service
@Transactional(isolation= Isolation.SERIALIZABLE)
public class KalahService {


    @Autowired
    private KalahRepository kalahRepository;

    public Kalah findByPlayerName (String name) {
        return kalahRepository.findKalahByPlayerOfKalahName(name);
    }

    public Kalah saveAndFlush (Kalah kalah) {
        return kalahRepository.saveAndFlush(kalah);
    }

   }

