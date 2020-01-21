import { Deserializable } from './deserializable';

export class User implements Deserializable {
    
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
    phoneNumber: string;
    city: string;
    jwttoken : string;
    idUser : number;
    status : string;
    userType: string;

    deserialize(input: any): this {
        Object.assign(this, input);
        return this;
    }
}