package com.example.globalweatherapp.db;

import androidx.annotation.NonNull;

import com.example.globalweatherapp.model.Device;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DeviceDao {

    private Realm mRealm;

    public DeviceDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final Device device) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(device);
            }
        });
    }

    public void save(final List<Device> userList) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(userList);
            }
        });
    }

    public Device loadAll() {
        return mRealm.where(Device.class).findFirst();
    }

    public Device loadAllAsync() {
        return mRealm.where(Device.class).findFirstAsync();
    }

//    public RealmObject loadBy(long id) {
//        return mRealm.where(User.class).equalTo("id", id).findFirst();
//    }

//    public void remove(@NonNull final RealmObject object) {
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                obj;
//            }
//        });
    //}

    public void removeAll() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.delete(Device.class);
            }
        });
    }

    public long count() {
        return mRealm.where(Device.class).count();
    }
}
