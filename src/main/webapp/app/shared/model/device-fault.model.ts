import { AbstractAuditing, IAbstractAuditing } from './abstract-auditing.model';

export interface IDeviceFault extends IAbstractAuditing {
  id?: number;
  name?: string;
  description?: string;
  urgency?: number;
  deviceId?: number;
  deviceName?: number;
}

export class DeviceFault extends AbstractAuditing implements IDeviceFault {
  constructor(public id?: number, public name?: string, public description?: string, public urgency?: number, public deviceId?: number) {
    super();
  }
}
