package mykalah.service;


import mykalah.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class KalahService {


    @Autowired
    private KalahRepository kalahRepository;

    @Autowired
    private PlayerService playerService;

    public Kalah findByPlayerName (String name) {
        return kalahRepository.findKalahByPlayerOfKalahName(name);
    }

    public Kalah createNewKalah (int i) {
        Kalah kalah = new Kalah();
        kalah.setStonesInKalah(i);
        return kalah;
    }
}

