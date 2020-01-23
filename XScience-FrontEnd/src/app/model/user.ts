import { Deserializable } from './deserializable';

export class User implements Deserializable {
    
    firstName: string;
    lastName: string;
    middleName: string;
    email: string;
    password: string;
    jwtToken? : string;
    
    role: string;

    deserialize(input: any): this {
        Object.assign(this, input);
        return this;
    }
}