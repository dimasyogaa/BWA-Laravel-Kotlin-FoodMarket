<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class UserSeeder extends Seeder
{
    // php artisan db:seed --class=UserSeeder
    public function run(): void
    {
        DB::table('users')->insert([
            'name' => 'Super Admin',
            'email' => 'superadmin@yogadimas.com',
            'password' => Hash::make('password'),
            'roles' => 'ADMIN',
            'address' => '123 Admin Street',
            'houseNumber' => '123A',
            'phoneNumber' => '1234567890',
            'city' => 'Admin City'
        ]);
    }
}
