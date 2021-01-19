import { IDevice } from 'app/shared/model/device.model';

export interface IDeviceModel {
  id?: number;
  name?: string;
  models?: IDevice[];
  producerId?: number;
  typeId?: number;
}

export class DeviceModel implements IDeviceModel {
  constructor(public id?: number, public name?: string, public models?: IDevice[], public producerId?: number, public typeId?: number) {}
}
