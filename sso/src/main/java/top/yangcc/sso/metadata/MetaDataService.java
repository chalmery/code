package top.yangcc.sso.metadata;

import top.yangcc.sso.metadata.entity.MetaDataDTO;

public interface MetaDataService<T extends MetaDataDTO> {



    T getDataValue(String code);


}
