package com.cominvi.app.oauth.services;

import java.util.List;
import com.cominvi.app.commons.general.Sistemas;

public interface ISistemasService {

  List<Sistemas> findAll();

  Sistemas findById(Long idsistema);

  Sistemas save(Sistemas sistema);

  Boolean saveAll(List<Sistemas> sistemas);

  Sistemas update(Sistemas sistema, Long idsistema);

  Boolean removeById(Long idsistema);
}
