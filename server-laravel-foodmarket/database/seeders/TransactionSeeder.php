<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class TransactionSeeder extends Seeder
{
    // php artisan db:seed --class=FoodSeeder
    public function run(): void
    {
        DB::table('transactions')->insert([
            'food_id' => 1,
            'user_id' => 1,
            'quantity' => 1,
            'total' => 41000,
            'status' => 'DELIVERED', // Assuming a default status
            'payment_url' => 'https://github.com/Yogadimas', // Assuming a placeholder URL
        ]);
    }
}
