import { IDeviceModel } from 'app/shared/model/device-model.model';

export interface IDeviceProducer {
  id?: number;
  name?: string;
  deviceModels?: IDeviceModel[];
}

export class DeviceProducer implements IDeviceProducer {
  constructor(public id?: number, public name?: string, public deviceModels?: IDeviceModel[]) {}
}
