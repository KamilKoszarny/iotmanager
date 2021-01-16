import { IDevice } from 'app/shared/model/device.model';
import { IDeviceProducer } from 'app/shared/model/device-producer.model';
import { IDeviceType } from 'app/shared/model/device-type.model';

export interface IDeviceModel {
  id?: number;
  name?: string;
  devices?: IDevice[];
  deviceProducer?: IDeviceProducer;
  deviceType?: IDeviceType;
}

export class DeviceModel implements IDeviceModel {
  constructor(
    public id?: number,
    public name?: string,
    public devices?: IDevice[],
    public deviceProducer?: IDeviceProducer,
    public deviceType?: IDeviceType
  ) {}
}
