package com.wms.mapper;

import com.wms.entity.StorageTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StorageTransactionMapper {
    List<StorageTransaction> getStorageTransactionByUid(Integer u_id);

    Integer insertStorageTransaction(StorageTransaction storageTransaction);

    StorageTransaction getStorageTransactionByItiId(Integer iti_id);

    Integer deleteStorageTransactionByItiId(Integer iti_id);

    Integer deleteStorageTransactionByUId(Integer u_id);
}
