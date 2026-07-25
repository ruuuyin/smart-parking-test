INSERT INTO parking_lots (lot_id, location, capacity) VALUES
('LOT_001', 'Downtown', 10),
('LOT_002', 'Uptown', 10),
('LOT_003', 'Suburb', 10);

INSERT INTO vehicles (license_plate, vehicle_type, owner_name) VALUES
('XYZ123', 'CAR', 'John Doe'),
('XYZ124', 'MOTORCYCLE', 'John Doe 2'),
('XYZ125', 'TRUCK', 'John Doe 3');

INSERT INTO parking_transactions (parking_lot_id, vehicle_license_plate, parking_status) VALUES
('LOT_001', 'XYZ123', 'CHECKED_IN'),
('LOT_002', 'XYZ124', 'CHECKED_IN'),
('LOT_003', 'XYZ125', 'CHECKED_OUT');