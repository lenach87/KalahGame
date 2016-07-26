package mykalah.service;


import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation= Isolation.SERIALIZABLE)
public class KalahService {

    public KalahService() {
    }

    @Autowired
    private KalahRepository kalahRepository;

    public Kalah save (Kalah kalah) {
        return kalahRepository.save(kalah);
    }

   }

