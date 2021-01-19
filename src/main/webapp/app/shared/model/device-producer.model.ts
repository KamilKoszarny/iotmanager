import { IDeviceModel } from 'app/shared/model/device-model.model';

export interface IDeviceProducer {
  id?: number;
  name?: string;
  models?: IDeviceModel[];
}

export class DeviceProducer implements IDeviceProducer {
  constructor(public id?: number, public name?: string, public models?: IDeviceModel[]) {}
}
