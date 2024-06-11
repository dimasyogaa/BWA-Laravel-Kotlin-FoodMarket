<?php

namespace Tests\Feature;

use Database\Seeders\FoodSeeder;
use Database\Seeders\TransactionSeeder;
use Database\Seeders\UserSeeder;
use Illuminate\Filesystem\Filesystem;
use Illuminate\Support\Facades\DB;
use Tests\TestCase;

class A0ResetTest extends TestCase
{

    public function test_reset(): void
    {
        DB::statement('SET FOREIGN_KEY_CHECKS=0;');
        DB::table('personal_access_tokens')->truncate();
        DB::table('sessions')->truncate();
        DB::table('users')->truncate();
//        DB::table('food')->truncate();
        DB::statement('SET FOREIGN_KEY_CHECKS=1;');

        $file = new Filesystem;
        $file->cleanDirectory(public_path('storage/assets/user'));
        $file->cleanDirectory(storage_path('app/public/assets/user'));


        $this->assertEquals(0, DB::table('personal_access_tokens')->count());
        $this->assertEquals(0, DB::table('sessions')->count());
        $this->assertEquals(0, DB::table('users')->count());
//        $this->assertEquals(0, DB::table('food')->count());
    }

    public function test_reset_token(): void
    {
        DB::statement('SET FOREIGN_KEY_CHECKS=0;');
        DB::table('personal_access_tokens')->truncate();
        DB::table('sessions')->truncate();
        DB::statement('SET FOREIGN_KEY_CHECKS=1;');

        $this->assertEquals(0, DB::table('personal_access_tokens')->count());
        $this->assertEquals(0, DB::table('sessions')->count());
    }

    public function test_reset_user(): void
    {
        DB::statement('SET FOREIGN_KEY_CHECKS=0;');
        DB::table('users')->truncate();
        DB::statement('SET FOREIGN_KEY_CHECKS=1;');

        $file = new Filesystem;
        $file->cleanDirectory(public_path('storage/assets/user'));
        $file->cleanDirectory(storage_path('app/public/assets/user'));

        $this->assertEquals(0, DB::table('users')->count());

        $this->seed([UserSeeder::class]);

        $this->assertEquals(1, DB::table('users')->count());
    }

    public function test_reset_food(): void
    {
        DB::statement('SET FOREIGN_KEY_CHECKS=0;');
        DB::table('food')->truncate();
        DB::statement('SET FOREIGN_KEY_CHECKS=1;');

        $this->assertEquals(0, DB::table('food')->count());

        $this->seed([FoodSeeder::class]);

        $this->assertEquals(10, DB::table('food')->count());
    }

    public function test_reset_transaction(): void
    {
        DB::statement('SET FOREIGN_KEY_CHECKS=0;');
        DB::table('transactions')->truncate();
        DB::statement('SET FOREIGN_KEY_CHECKS=1;');

        $this->assertEquals(0, DB::table('transactions')->count());

        $this->seed([TransactionSeeder::class]);

        $this->assertEquals(1, DB::table('transactions')->count());
    }
}
