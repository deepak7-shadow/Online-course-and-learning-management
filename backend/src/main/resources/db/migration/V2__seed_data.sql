-- ========================================================
-- V2__seed_data.sql
-- Seed initial LMS categories
-- ========================================================

INSERT INTO categories (id, name, slug, description, is_active)
VALUES
    ('a0000000-0000-0000-0000-000000000001', 'Web Development', 'web-development', 'Learn frontend, backend, and full-stack web technologies.', true),
    ('a0000000-0000-0000-0000-000000000002', 'Data Science & AI', 'data-science-ai', 'Machine learning, data analytics, Python, and generative AI.', true),
    ('a0000000-0000-0000-0000-000000000003', 'Cloud & DevOps', 'cloud-devops', 'AWS, GCP, Docker, Kubernetes, CI/CD, and infrastructure.', true),
    ('a0000000-0000-0000-0000-000000000004', 'Mobile Development', 'mobile-development', 'Build mobile apps with Flutter, React Native, iOS, and Android.', true),
    ('a0000000-0000-0000-0000-000000000005', 'Cybersecurity', 'cybersecurity', 'Network security, ethical hacking, and secure application development.', true)
ON CONFLICT (slug) DO NOTHING;
