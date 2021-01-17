import { IDevice } from 'app/shared/model/device.model';

export interface IDeviceModel {
  id?: number;
  name?: string;
  devices?: IDevice[];
  deviceProducerId?: number;
  deviceTypeId?: number;
}

export class DeviceModel implements IDeviceModel {
  constructor(
    public id?: number,
    public name?: string,
    public devices?: IDevice[],
    public deviceProducerId?: number,
    public deviceTypeId?: number
  ) {}
}
