export interface IDeviceFault {
  id?: number;
  name?: string;
  description?: string;
  urgency?: number;
  deviceId?: number;
  deviceName?: number;
}

export class DeviceFault implements IDeviceFault {
  constructor(public id?: number, public name?: string, public description?: string, public urgency?: number, public deviceId?: number) {}
}
