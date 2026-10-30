package com.example.data

import androidx.annotation.DrawableRes

data class LuxuryProduct(
    val id: String,
    val brand: String,
    val title: String,
    val category: String, // "Men", "Women", "Bags", "Perfumes", "Kids", "VIP Vault"
    val priceInINR: Long,
    val description: String,
    val material: String,
    val origin: String,
    val remainingStock: Int,
    val isLimitedDrop: Boolean = false,
    val sizes: List<String> = listOf("EU 48", "EU 50", "EU 52", "One Size"),
    val imageUrl: String? = "https://picsum.photos/400/400"
)

object LuxuryCatalog {
    val categories = listOf(
        "All Luxury",
        "Men's Luxury",
        "Women's Luxury",
        "Kids",
        "Bags",
        "Perfumes",
        "Hype Drops",
        "Satirical Brand Registry"
    )

    val products = listOf(
        // Satirical Brand Registry / Parody Items
        LuxuryProduct(
            id = "satire_01",
            brand = "FOLEX",
            title = "Oyster Fake Perpetual",
            category = "Satirical Brand Registry",
            priceInINR = 1200,
            description = "A stunningly inaccurate timepiece guaranteed to lose 5 minutes every hour. Comes with a green cardboard box.",
            material = "Painted Plastic & Lead",
            origin = "Canal Street, NY",
            remainingStock = 500,
            sizes = listOf("40 mm", "41 mm")
        ),
        LuxuryProduct(
            id = "satire_02",
            brand = "GHUMATO",
            title = "Artisanal Biryani Delivery Bag",
            category = "Hype Drops",
            priceInINR = 2500,
            description = "The ultimate hype beast accessory. Insulated to keep your clout warm and your food cold.",
            material = "Recycled Delivery Uniforms",
            origin = "Mumbai Streets",
            remainingStock = 50,
            isLimitedDrop = true,
            sizes = listOf("Large")
        ),
        LuxuryProduct(
            id = "satire_03",
            brand = "ZEPTOUT",
            title = "10-Minute Grocery Puffer",
            category = "Hype Drops",
            priceInINR = 8500,
            description = "Why wait for fashion when you can have it in 10 minutes? Bright purple puffer with excessive branding.",
            material = "100% Synthetic Nylon",
            origin = "Local Dark Store",
            remainingStock = 10,
            isLimitedDrop = true,
            sizes = listOf("M", "L", "XL")
        ),
        LuxuryProduct(
            id = "satire_04",
            brand = "PRADO",
            title = "Re-Nylon Grocery Tote",
            category = "Satirical Brand Registry",
            priceInINR = 1500,
            description = "Looks like a luxury tote, functions like a Sabzi Mandi bag. Complete with a misspelled triangle logo.",
            material = "Recycled Plastic Bags",
            origin = "Local Market",
            remainingStock = 100,
            sizes = listOf("One Size")
        ),
        LuxuryProduct(
            id = "satire_05",
            brand = "GOOCI",
            title = "GG Supreme Chappals",
            category = "Satirical Brand Registry",
            priceInINR = 800,
            description = "The classic hawai chappal elevated with fake interlocking Gs. Perfect for local errands.",
            material = "Rubber",
            origin = "Bandra Linking Road",
            remainingStock = 200,
            sizes = listOf("UK 7", "UK 8", "UK 9")
        ),

        // Luxury Bags
        LuxuryProduct(
            id = "bag_01",
            brand = "HERMÈS PARIS",
            title = "Birkin 30 Noir Togo Leather",
            category = "Bags",
            priceInINR = 1850000,
            description = "Handcrafted in Paris with signature Noir Togo calfskin, gold-plated hardware, turn-lock closure, and protective bottom feet.",
            material = "100% Togo Calfskin Leather, 24K Gold Plated Hardware",
            origin = "Paris, France",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("30 cm", "35 cm")
        ),
        LuxuryProduct(
            id = "bag_02",
            brand = "CHRISTIAN DIOR",
            title = "Lady Dior Cannage Lambskin Bag",
            category = "Bags",
            priceInINR = 490000,
            description = "Quilted Noir lambskin featuring iconic Cannage stitching, pale gold DIOR charms, and detachable shoulder strap.",
            material = "Lambskin & Pale Gold Hardware",
            origin = "Florence, Italy",
            remainingStock = 3,
            sizes = listOf("Small", "Medium")
        ),
        LuxuryProduct(
            id = "bag_03",
            brand = "SAINT LAURENT",
            title = "Icare Maxi Shopping Bag",
            category = "Bags",
            priceInINR = 380000,
            description = "Maxi tote in quilted lambskin leather with iconic sculpted YSL metallic logo and removable zip pouch.",
            material = "Quilted Lambskin Leather",
            origin = "Paris, France",
            remainingStock = 2,
            sizes = listOf("Maxi")
        ),
        LuxuryProduct(
            id = "bag_04",
            brand = "BOTTEGA VENETA",
            title = "Intrecciato Large Cabat Tote",
            category = "Bags",
            priceInINR = 620000,
            description = "Seamless double-face hand-woven leather tote reflecting master Italian artisan craftsmanship.",
            material = "Intrecciato Nappa Leather",
            origin = "Vicenza, Italy",
            remainingStock = 2,
            sizes = listOf("Large")
        ),

        // Men's Couture
        LuxuryProduct(
            id = "men_01",
            brand = "SAINT LAURENT",
            title = "Grain de Poudre Tuxedo Jacket",
            category = "Men's Couture",
            priceInINR = 345000,
            description = "Peak lapel single-breasted tuxedo jacket woven from ultra-fine Italian wool with silk satin lapel lining.",
            material = "100% Virgin Wool, Silk Satin Trim",
            origin = "Milan, Italy",
            remainingStock = 2,
            sizes = listOf("EU 48", "EU 50", "EU 52", "EU 54")
        ),
        LuxuryProduct(
            id = "men_02",
            brand = "TOM FORD",
            title = "Shelford Leather Biker Jacket",
            category = "Men's Couture",
            priceInINR = 590000,
            description = "Heavyweight grain calfskin jacket with custom gunmetal asymmetrical zippers and quilted satin interior.",
            material = "100% Full Grain Calfskin",
            origin = "Parma, Italy",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("EU 50", "EU 52")
        ),
        LuxuryProduct(
            id = "men_03",
            brand = "ROLEX",
            title = "Cosmograph Daytona Oystersteel",
            category = "Men's Couture",
            priceInINR = 2850000,
            description = "Monobloc Cerachrom bezel in black ceramic with tachymetric scale and white chronograph dial.",
            material = "904L Oystersteel & Cerachrom Ceramic",
            origin = "Geneva, Switzerland",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("40 mm")
        ),

        // Women's Haute
        LuxuryProduct(
            id = "women_01",
            brand = "CHANEL",
            title = "Haute Couture Tweed Jacket Noir",
            category = "Women's Haute",
            priceInINR = 780000,
            description = "Signature Lesage hand-braided tweed with CC lion head jewel buttons and silk camellia lining.",
            material = "Wool Tweed & Mulberry Silk",
            origin = "Paris Atelier, France",
            remainingStock = 2,
            sizes = listOf("FR 36", "FR 38", "FR 40")
        ),
        LuxuryProduct(
            id = "women_02",
            brand = "CARTIER",
            title = "Love Bracelet 18K White Gold",
            category = "Women's Haute",
            priceInINR = 3200000,
            description = "Set with 204 brilliant-cut diamonds totaling 1.99 carats, locking screw system included.",
            material = "18K White Gold & Brilliant Diamonds",
            origin = "Geneva, Switzerland",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("16 cm", "17 cm", "18 cm")
        ),

        // Premium Perfumes
        LuxuryProduct(
            id = "perfume_01",
            brand = "MAISON FRANCIS KURKDJIAN",
            title = "Baccarat Rouge 540 Extrait (200ml)",
            category = "Perfumes",
            priceInINR = 85000,
            description = "Luminous amber floral fragrance with Jasmine Grandiflorum, Bitter Almond, Ambergris, and Cedarwood notes.",
            material = "High Concentration Extrait de Parfum",
            origin = "Paris, France",
            remainingStock = 4,
            sizes = listOf("70 ml", "200 ml")
        ),
        LuxuryProduct(
            id = "perfume_02",
            brand = "TOM FORD",
            title = "Private Blend Oud Wood Intense",
            category = "Perfumes",
            priceInINR = 52000,
            description = "Unsparingly rich fragrance capturing rare oud wood, sandalwood, Chinese pepper, and amber.",
            material = "Eau de Parfum",
            origin = "London, UK",
            remainingStock = 5,
            sizes = listOf("50 ml", "100 ml")
        ),
        LuxuryProduct(
            id = "perfume_03",
            brand = "CREED",
            title = "Aventus Millésime Batch 2026",
            category = "Perfumes",
            priceInINR = 45000,
            description = "Hand-crafted fragrance celebrating strength and vision with Blackcurrant, Italian Bergamot, Birch, and Musk.",
            material = "Eau de Parfum Millésime",
            origin = "Fontainebleau, France",
            remainingStock = 3,
            sizes = listOf("100 ml")
        ),

        // Kids Atelier
        LuxuryProduct(
            id = "kids_01",
            brand = "BABY DIOR",
            title = "Cashmere Onesie & Bonnet Gift Set",
            category = "Kids",
            priceInINR = 115000,
            description = "Ultra-soft 100% Mongolian cashmere knit onesie with tonal Cannage embroidery and matching bonnet.",
            material = "100% Mongolian Cashmere",
            origin = "Milan, Italy",
            remainingStock = 3,
            sizes = listOf("0-3M", "3-6M", "6-12M")
        ),
        LuxuryProduct(
            id = "kids_02",
            brand = "GUCCI KIDS",
            title = "GG Supreme Monogram Backpack",
            category = "Kids",
            priceInINR = 88000,
            description = "Classic GG canvas backpack with adjustable padded nylon shoulder straps and leather trim.",
            material = "GG Supreme Canvas & Italian Calfskin",
            origin = "Florence, Italy",
            remainingStock = 4,
            sizes = listOf("One Size")
        ),

        // Shoes & Sneakers
        LuxuryProduct(
            id = "shoes_01",
            brand = "BALENCIAGA",
            title = "Triple S Clear Sole Sneakers",
            category = "Men's Luxury",
            priceInINR = 125000,
            description = "Iconic triple-stacked sole sneakers in complex 3-layer clear air bubble outsole with embroidered size on toe.",
            material = "Calfskin, Lambskin & Mesh Construction",
            origin = "Paris, France",
            remainingStock = 3,
            isLimitedDrop = true,
            sizes = listOf("UK 7", "UK 8", "UK 9", "UK 10")
        ),
        LuxuryProduct(
            id = "shoes_02",
            brand = "CHRISTIAN LOUBOUTIN",
            title = "Greggo Patent Red Sole Oxford",
            category = "Men's Luxury",
            priceInINR = 110000,
            description = "Elongated silhouette lace-up dress Oxford in glossy patent black leather with trademark signature red lacquered sole.",
            material = "100% Italian Patent Leather",
            origin = "Milan, Italy",
            remainingStock = 2,
            sizes = listOf("UK 8", "UK 9", "UK 10")
        ),
        LuxuryProduct(
            id = "shoes_03",
            brand = "PRADA",
            title = "Cloudbust Thunder Knit Sneakers",
            category = "Men's Luxury",
            priceInINR = 135000,
            description = "Sculptural 3D rubber sawtooth tread sole with breathable technical mesh upper and tonal metal eyelets.",
            material = "Technical Fabric & Injected Rubber",
            origin = "Florence, Italy",
            remainingStock = 4,
            sizes = listOf("UK 7", "UK 8", "UK 9")
        ),

        // Jackets & Outerwear
        LuxuryProduct(
            id = "jacket_01",
            brand = "MONCLER",
            title = "Maya Short Down Puffer Jacket",
            category = "Women's Luxury",
            priceInINR = 195000,
            description = "Classic glossy boudin-quilted down jacket with detachable hood, snap flap sleeve pocket, and felt logo crest.",
            material = "Direct-Injected Goose Down & Lacquer Nylon",
            origin = "Grenoble, France",
            remainingStock = 2,
            isLimitedDrop = true,
            sizes = listOf("EU 48", "EU 50", "EU 52")
        ),
        LuxuryProduct(
            id = "jacket_02",
            brand = "SAINT LAURENT",
            title = "Teddy Leather-Trim Varsity Jacket",
            category = "Women's Luxury",
            priceInINR = 285000,
            description = "Iconic wool-blend varsity jacket with white lambskin leather shoulder piping, snap front, and striped rib trim.",
            material = "90% Virgin Wool, 10% Lambskin",
            origin = "Paris, France",
            remainingStock = 3,
            sizes = listOf("EU 48", "EU 50", "EU 52")
        ),
        LuxuryProduct(
            id = "jacket_03",
            brand = "TOM FORD",
            title = "Grain Calfskin Shearling Aviator",
            category = "Women's Luxury",
            priceInINR = 680000,
            description = "Hand-buffed espresso grain calfskin aviator lined with plush cream Spanish lamb shearling collar.",
            material = "100% Spanish Lamb Shearling & Grain Leather",
            origin = "Parma, Italy",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("EU 50", "EU 52")
        ),

        // Shorts & Apparel
        LuxuryProduct(
            id = "shorts_01",
            brand = "PRADA",
            title = "Re-Nylon Technical Cargo Shorts",
            category = "Men's Luxury",
            priceInINR = 85000,
            description = "Sustainable regenerated nylon cargo shorts featuring enameled metal triangle logo and twin zip utility pockets.",
            material = "100% Ocean-Recycled Re-Nylon",
            origin = "Milan, Italy",
            remainingStock = 5,
            sizes = listOf("Small", "Medium", "Large")
        ),
        LuxuryProduct(
            id = "shorts_02",
            brand = "FEAR OF GOD",
            title = "Eternal Silk Drawstring Lounge Shorts",
            category = "Men's Luxury",
            priceInINR = 72000,
            description = "Luxe mulberry silk lounge shorts with leather waist drawstring, relaxed leg opening, and horn buttons.",
            material = "100% Heavyweight Italian Silk Charmeuse",
            origin = "Los Angeles, USA",
            remainingStock = 4,
            sizes = listOf("Medium", "Large")
        ),
        LuxuryProduct(
            id = "shorts_03",
            brand = "BALMAIN PARIS",
            title = "Embroidered Monogram Sweat Shorts",
            category = "Men's Luxury",
            priceInINR = 65000,
            description = "Heavy french terry cotton sweat shorts with high-density embroidered gold PB crest logo.",
            material = "100% Organic French Terry Cotton",
            origin = "Paris, France",
            remainingStock = 3,
            sizes = listOf("Medium", "Large", "X-Large")
        ),

        // Watches & Jewelry
        LuxuryProduct(
            id = "watch_01",
            brand = "PATEK PHILIPPE",
            title = "Nautilus 5711/1A Blue Dial",
            category = "Women's Luxury",
            priceInINR = 9800000,
            description = "Pore-less blue horizontal embossed dial, stainless steel octagonal bezel, sapphire crystal caseback.",
            material = "Stainless Steel & Sapphire Crystal",
            origin = "Geneva, Switzerland",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("40 mm")
        ),
        LuxuryProduct(
            id = "watch_02",
            brand = "AUDEMARS PIGUET",
            title = "Royal Oak Chronograph Titanium",
            category = "Women's Luxury",
            priceInINR = 7200000,
            description = "Grande Tapisserie motif dial, integrated titanium bracelet, caliber 4401 automatic chronograph.",
            material = "Grade 5 Titanium & Anti-Reflective Sapphire",
            origin = "Le Brassus, Switzerland",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("41 mm")
        ),

        // VIP Vault
        LuxuryProduct(
            id = "vault_01",
            brand = "SAPOLSKY BESPOKE ATELIER",
            title = "Hyper-Dopamine Custom Chronograph",
            category = "VIP Vault",
            priceInINR = 4500000,
            description = "Ultra-exclusive 1-of-1 time-piece crafted exclusively for NOIR Black Card members. Features exposed skeletonized balance wheel.",
            material = "Grade 5 Titanium & Obsidian Crystal",
            origin = "Le Locle, Switzerland",
            remainingStock = 1,
            isLimitedDrop = true,
            sizes = listOf("42 mm Custom")
        )
    )
}
