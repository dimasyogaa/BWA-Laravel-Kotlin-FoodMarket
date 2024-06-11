<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class FoodSeeder extends Seeder
{
    // php artisan db:seed --class=FoodSeeder
    public function run(): void
    {
        DB::table('food')->insert([
            [
                'picturePath' => 'assets/food/lasagna.jpeg',
                'name' => 'Lasagna',
                'description' => 'A classic Italian lasagna made with ground beef, layers of pasta, ricotta cheese, and tomato sauce.',
                'ingredients' => 'Pasta, ground beef, ricotta cheese, tomato sauce, mozzarella cheese',
                'price' => 30000,
                'rate' => 4.7,
                'types' => 'new_food, recommended, popular',
            ],
            [
                'picturePath' => 'assets/food/falafel.jpeg',
                'name' => 'Falafel',
                'description' => 'A popular Middle Eastern dish made of deep-fried balls or patties of spiced, ground chickpeas.',
                'ingredients' => 'Chickpeas, onions, garlic, parsley, cumin, coriander',
                'price' => 20000,
                'rate' => 4.6,
                'types' => 'recommended, popular',
            ],
            [
                'picturePath' => 'assets/food/paella.jpeg',
                'name' => 'Paella',
                'description' => 'A Spanish rice dish made with chicken, seafood, vegetables, and saffron.',
                'ingredients' => 'Rice, chicken, seafood, vegetables, saffron, peas',
                'price' => 40000,
                'rate' => 4.9,
                'types' => 'new_food, recommended',
            ],
            [
                'picturePath' => 'assets/food/moussaka.jpeg',
                'name' => 'Moussaka',
                'description' => 'A Greek casserole made with layers of eggplant, ground lamb, and béchamel sauce.',
                'ingredients' => 'Eggplant, ground lamb, tomatoes, béchamel sauce, onions, garlic',
                'price' => 28000,
                'rate' => 4.6,
                'types' => 'new_food, recommended',
            ],
            [
                'picturePath' => 'assets/food/shakshuka.jpeg',
                'name' => 'Shakshuka',
                'description' => 'A North African and Mediterranean dish of poached eggs in a sauce of tomatoes, chili peppers, and onions, spiced with cumin.',
                'ingredients' => 'Eggs, tomatoes, chili peppers, onions, cumin, garlic',
                'price' => 20000,
                'rate' => 4.5,
                'types' => 'popular',
            ],
            [
                'picturePath' => 'assets/food/hummus.jpeg',
                'name' => 'Hummus',
                'description' => 'A creamy Middle Eastern dip made from blended chickpeas, tahini, olive oil, lemon juice, and garlic.',
                'ingredients' => 'Chickpeas, tahini, olive oil, lemon juice, garlic',
                'price' => 15000,
                'rate' => 4.8,
                'types' => 'recommended, popular',
            ],
            [
                'picturePath' => 'assets/food/tabbouleh.jpeg',
                'name' => 'Tabbouleh',
                'description' => 'A fresh, herby Levantine salad made from finely chopped parsley, tomatoes, mint, onion, and bulgur, seasoned with olive oil, lemon juice, and salt.',
                'ingredients' => 'Parsley, tomatoes, mint, onion, bulgur, olive oil, lemon juice',
                'price' => 18000,
                'rate' => 4.7,
                'types' => 'new_food, recommended',
            ],
            [
                'picturePath' => 'assets/food/biryani.jpeg',
                'name' => 'Biryani',
                'description' => 'A fragrant Indian rice dish made with basmati rice, spices, and either chicken, beef, or vegetables.',
                'ingredients' => 'Basmati rice, chicken, spices, yogurt, onions, garlic, ginger',
                'price' => 35000,
                'rate' => 4.9,
                'types' => 'popular',
            ],
            [
                'picturePath' => 'assets/food/samosa.jpeg',
                'name' => 'Samosa',
                'description' => 'A fried or baked pastry with a savory filling, such as spiced potatoes, peas, and sometimes meat.',
                'ingredients' => 'Flour, potatoes, peas, spices, oil',
                'price' => 12000,
                'rate' => 4.6,
                'types' => 'new_food, recommended',
            ],
            [
                'picturePath' => 'assets/food/baklava.jpeg',
                'name' => 'Baklava',
                'description' => 'A rich, sweet dessert pastry made of layers of filo filled with chopped nuts and sweetened with syrup or honey.',
                'ingredients' => 'Filo dough, nuts, butter, syrup, honey',
                'price' => 25000,
                'rate' => 4.8,
                'types' => 'popular',
            ]
        ]);
    }
}
