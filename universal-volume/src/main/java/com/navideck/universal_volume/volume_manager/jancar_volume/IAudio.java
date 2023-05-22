package com.navideck.universal_volume.volume_manager.jancar_volume;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

interface IAudio extends IInterface {
    int getParam(int i) throws RemoteException;

    int getParamMaxValue(int i) throws RemoteException;

    int getParamMinValue(int i) throws RemoteException;

    boolean isParamAvailable(int i) throws RemoteException;

    void setParam(int i, int i2) throws RemoteException;

    abstract class Stub extends Binder implements IAudio {
        private static final String DESCRIPTOR = "com.jancar.services.audio.IAudio";
        private static final int TRANSACTION_isParamAvailable = 4;
        private static final int TRANSACTION_getParamMinValue = 5;
        private static final int TRANSACTION_getParamMaxValue = 6;
        private static final int TRANSACTION_getParam = 8;
        private static final int TRANSACTION_setParam = 9;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAudio asInterface(IBinder iBinder) {
            if (iBinder == null) return null;
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (!(queryLocalInterface instanceof IAudio)) return new Proxy(iBinder);
            return (IAudio) queryLocalInterface;
        }

        public static IAudio getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                case TRANSACTION_isParamAvailable:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isParamAvailable = isParamAvailable(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(isParamAvailable ? 1 : 0);
                    return true;
                case TRANSACTION_getParamMinValue:
                    parcel.enforceInterface(DESCRIPTOR);
                    int paramMinValue = getParamMinValue(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(paramMinValue);
                    return true;
                case TRANSACTION_getParamMaxValue:
                    parcel.enforceInterface(DESCRIPTOR);
                    int paramMaxValue = getParamMaxValue(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(paramMaxValue);
                    return true;
                case TRANSACTION_getParam:
                    parcel.enforceInterface(DESCRIPTOR);
                    int param = getParam(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(param);
                    return true;
                case TRANSACTION_setParam:
                    parcel.enforceInterface(DESCRIPTOR);
                    setParam(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAudio {
            public static IAudio sDefaultImpl;

            private final IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public boolean isParamAvailable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    if (!this.mRemote.transact(TRANSACTION_isParamAvailable, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isParamAvailable(i);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getParamMinValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(TRANSACTION_getParamMinValue, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getParamMinValue(i);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getParamMaxValue(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(TRANSACTION_getParamMaxValue, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getParamMaxValue(i);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getParam(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(TRANSACTION_getParam, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getParam(i);
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setParam(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(TRANSACTION_setParam, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setParam(i, i2);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

        }
    }
}
